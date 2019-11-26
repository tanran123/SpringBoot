package com.ginko.driver.api.moudle.pay;

import com.ginko.driver.framework.service.UserIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: tran
 * @Date: 2019/11/26
 * @Description:
 */
@RequestMapping("/balancePay")
@RestController
public class PayController {
    @Autowired
    private UserIncomeService userIncomeService;
}
