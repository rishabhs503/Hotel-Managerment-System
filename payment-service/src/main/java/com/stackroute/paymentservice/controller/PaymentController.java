package com.stackroute.paymentservice.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stackroute.paymentservice.config.Producer;
import com.stackroute.paymentservice.model.OrderRequest;
import com.stackroute.paymentservice.model.OrderResponse;
import com.stackroute.paymentservice.payloads.PaymentResponseDTO;
import com.stackroute.paymentservice.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Rishabh Saksena
 * This class is used for checkout and payment.
 */
@ApiOperation(value = "/payment", tags = "Payment Gateway")
@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static final String SECRET_ID1 = "rzp_test_81cLJdyBoC23FP";
	private static final String SECRET_KEY1 = "UJtZQxQYdqwT3FhyWdpLUMQo";
	private Order order;

	@Autowired
	private Producer producer;

	@Autowired
	private OrderService orderService;

	/**
	 * This method is used to set the required attribute in html page
	 * and to redirect to the payment page for payment.
	 * @param model Used to add attribute to send to the html page
	 * @return Payment html page
	 */
    @RequestMapping("/pay")
    public String getHomeInit(Model model) {
    	OrderResponse response = new OrderResponse();
    	List<OrderRequest> request = orderService.getOrders();
		if(request != null) {
			for (OrderRequest request1 : request) {
				if (request1.getAmount() > 0) {
					try {
						order = createOrder(request1);
						int orderAmount = order.get("amount");
						double amt = (double) orderAmount / 100;
						response.setRazorpayOrderId(order.get("id"));
						response.setApplicationFee(amt);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (response.getRazorpayOrderId() != null) {
						model.addAttribute("razorpayKey", SECRET_ID1);
						model.addAttribute("orderId", response.getRazorpayOrderId());
						model.addAttribute("name", request1.getCustomerName());
						model.addAttribute("email", request1.getEmail());
						model.addAttribute("phoneNumber", request1.getPhoneNumber());
						model.addAttribute("customerId", request1.getOrderId());
						return "paymentPage";
					} else {
						System.out.println("No order id generated");
					}
				} else {
					return "paymentFail";
				}
			}
		}
		return "NoPayment";
    }

	/**
	 * This method is used to get details from the post submission of data retrieved from razorpay in html page.
	 * @param razorpayPaymentId returned razorpay payment id from html page when payment is completed
	 * @param razorpayOrderId returned razorpay order id from html page when payment is completed
	 * @param razorpaySignature returned razorpay signature from html page when payment is completed
	 * @param customerId returned customer id from html page from whom payment is completed
	 * @return Success html page if payment is successful
	 */
	 @RequestMapping(value = {"/success"}, method = RequestMethod.POST)
	 public String paymentSuccess(@RequestParam("razorpay_payment_id") String razorpayPaymentId,
	                                 @RequestParam("razorpay_order_id") String razorpayOrderId,
	                                 @RequestParam("razorpay_signature") String razorpaySignature,
								  	 @RequestParam("razorpay_customer_id") int customerId
	    ){
		 System.out.println("Save all data, which on success we get!");
		 orderService.deleteOrderById(customerId);
		 PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
		 paymentResponseDTO.setPaymentId(razorpayPaymentId);
		 paymentResponseDTO.setOrderId(razorpayOrderId);
		 paymentResponseDTO.setPaymentSignature(razorpaySignature);
		 paymentResponseDTO.setBookingId(customerId);
		 System.out.println("PaymentResponseDTO: "+paymentResponseDTO);
		 producer.sendMessageToRabbitMq(paymentResponseDTO);
		 return "successPage";
	    }

		private Order createOrder(OrderRequest request) throws RazorpayException {
			RazorpayClient client = new RazorpayClient(SECRET_ID1, SECRET_KEY1);
			JSONObject orderRequest1 = new JSONObject();
			double amount = request.getAmount()*100;
			orderRequest1.put("amount",amount);
			orderRequest1.put("currency","INR");
			orderRequest1.put("receipt", "receipt#1");
			orderRequest1.put("payment_capture",true);

			return client.orders.create(orderRequest1);
		}
}
