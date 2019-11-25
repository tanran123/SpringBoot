package com.ginko.driver.api.moudle;

import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.tolls.TokenTools;
import com.ginko.driver.framework.entity.CommodityInfo;
import com.ginko.driver.framework.entity.UserOrderInfo;
import com.ginko.driver.framework.service.CommodityService;
import com.ginko.driver.framework.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private CommodityService commodityService;

    @RequestMapping("/queryOrder")
    public MsgConfig queryOrder(@RequestBody UserOrderInfo userOrderInfo, HttpServletRequest request){
        try {
            Integer userId = TokenTools.getUserIdFromToken(request.getHeader("Authorization"));
            if (userId!=userOrderInfo.getUserId()){
                return new MsgConfig("401","权限不足",null);
            }
            userOrderInfo.setUserId(userId);
        }
        catch (Exception e){
            return new MsgConfig("401","权限不足",null);
        }

        return new MsgConfig("0",null,orderInfoService.findByUserOrder(userOrderInfo));
    }

    @RequestMapping("/updateOrderStatus")
    public MsgConfig updateOrderStatus(@RequestBody UserOrderInfo userOrderInfo, HttpServletRequest request){
        try {
            Integer userId = TokenTools.getUserIdFromToken(request.getHeader("Authorization"));
            if (userId!=userOrderInfo.getUserId()){
                return new MsgConfig("401","权限不足",null);
            }
            userOrderInfo.setUserId(userId);
        }
        catch (Exception e){
            return new MsgConfig("401","权限不足",null);
        }

        return new MsgConfig("0",null,orderInfoService.updateUserOrder(userOrderInfo));
    }

    @RequestMapping("/getCommodity")
    public MsgConfig getCommodity(@RequestBody CommodityInfo commodityInfo, HttpServletRequest request){
        try {
            Integer userId = TokenTools.getUserIdFromToken(request.getHeader("Authorization"));
            if (userId!=commodityInfo.getUserId()){
                return new MsgConfig("401","权限不足",null);
            }
            commodityInfo.setUserId(userId);
        }
        catch (Exception e){
            return new MsgConfig("401","权限不足",null);
        }

        return new MsgConfig("0",null,commodityService.findByUserId(commodityInfo));
    }


    @RequestMapping("/updatePrice")
    public MsgConfig updatePrice(@RequestBody CommodityInfo commodityInfo, HttpServletRequest request){
        try {
            Integer userId = TokenTools.getUserIdFromToken(request.getHeader("Authorization"));
            if (userId!=commodityInfo.getUserId()){
                return new MsgConfig("401","权限不足",null);
            }
            commodityInfo.setUserId(userId);
        }
        catch (Exception e){
            return new MsgConfig("401","权限不足",null);
        }

        return new MsgConfig("0",null,commodityService.updateCommodityPrice(commodityInfo));
    }

    @RequestMapping("/updateSellStatus")
    public MsgConfig updateSellStatus(@RequestBody CommodityInfo commodityInfo, HttpServletRequest request){
        try {
            Integer userId = TokenTools.getUserIdFromToken(request.getHeader("Authorization"));
            if (userId!=commodityInfo.getUserId()){
                return new MsgConfig("401","权限不足",null);
            }
            commodityInfo.setUserId(userId);
        }
        catch (Exception e){
            return new MsgConfig("401","权限不足",null);
        }

        return new MsgConfig("0",null,commodityService.updateSellStatus(commodityInfo));
    }
}
