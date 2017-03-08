package com.aspire.webbas.portal.modules.login.web;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aspire.webbas.portal.common.util.CheckCodeUtil;

@Controller
@RequestMapping("/code")
public class CodeController { 
 
    @RequestMapping("/getCode.ajax") 
    public void getCode(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException { 
 
        HttpSession session = req.getSession(); 

 
        BufferedImage image = CheckCodeUtil.createImage(session);
        		
        // 禁止图像缓存。  
        resp.setHeader("Pragma", "no-cache"); 
        resp.setHeader("Cache-Control", "no-cache"); 
        resp.setDateHeader("Expires", 0); 
 
        resp.setContentType("image/jpeg"); 
 
        // 将图像输出到Servlet输出流中。  
        ServletOutputStream sos = resp.getOutputStream(); 
        ImageIO.write(image, "jpeg", sos); 
        sos.close(); 
    } 
 
} 