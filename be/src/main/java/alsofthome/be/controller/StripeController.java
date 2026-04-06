package alsofthome.be.controller;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
@CrossOrigin(origins = "http://localhost:3000")
public class StripeController {

    // ⚠️ pune cheia ta SECRET aici
    static {
        Stripe.apiKey = "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";
    }

    @PostMapping("/create-payment-intent")
    public Map<String, String> createPaymentIntent(@RequestBody Map<String, Object> data) throws Exception {

        int amount = (int) data.get("amount"); // în bani (ex: 10000 = 100 lei)

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) amount)
                        .setCurrency("ron")
                        .build();

        PaymentIntent intent = PaymentIntent.create(params);

        Map<String, String> response = new HashMap<>();
        response.put("clientSecret", intent.getClientSecret());

        return response;
    }
}
