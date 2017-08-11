package nl.bsoft.Controller;

import nl.bsoft.data.Order;
import nl.bsoft.sender.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class OrderController {

    private final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final String orderQueue = "order-queue";

    @Autowired
    private Sender sender;

    private final int SUCCESS = 0;

    @RequestMapping(path ="/order/store", method = RequestMethod.POST)
    public int store(@RequestParam long itemCode, @RequestParam String itemName, @RequestParam int itemQuantity) {
        int status = SUCCESS;

        log.info("Received itemCode: '{}', itemName: '{}', itemQuantity: '{}'", itemCode, itemName, itemQuantity);

        try {
            sender.send(orderQueue, new Order(itemCode, itemName, itemQuantity));
        } catch (Exception e) {
            log.error("Could not place order", e);
        }

        return status;
    }

    @RequestMapping(path = "/order/get", method = RequestMethod.GET)
    public Order get(@RequestParam long itemCode) {
        int status = SUCCESS;

        log.info("Received itemCode: '{}'", itemCode);
        Order order = new Order(itemCode, "Http test", 1);

        return order;
    }


//
//    public @ResponseBody Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
//        return new Greeting(counter.incrementAndGet(), String.format(template, name));
//    }
}
