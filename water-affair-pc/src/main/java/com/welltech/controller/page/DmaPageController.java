package com.welltech.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.entity.Dma;
import com.welltech.waterAffair.service.DmaService;

@Controller
public class DmaPageController {

	@Autowired
	private DmaService dmaService;
	
	@RequestMapping(value = { "/addDmaArea" }, method = RequestMethod.POST)
    public String addDmaManage(@RequestParam String dmaName) {
        Integer userId = UserUtils.getUserId();
        Dma dma=new Dma();
        dma.setName(dmaName);
        dma.setUserId(userId);;
        dmaService.save(dma);

        return "redirect:/dmaManage";
    }
	
}
