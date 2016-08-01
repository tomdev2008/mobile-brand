package com.xiu.mobile.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.email.bean.EmailBean;
import com.xiu.mobile.portal.ei.EIEmailManager;
import com.xiu.mobile.portal.service.IEmailService;

@Service("emailService")
public class IEmailServiceImpl implements IEmailService{
	
	@Autowired
	private EIEmailManager eiEmailManager;

	@Override
	public boolean sendEmail(String emailAddr, String subject, String body,
			String creator, String senderName) {
		// TODO Auto-generated method stub
		EmailBean eb = new EmailBean();
		eb.setCreator(creator);
		eb.setReceiverMail(emailAddr);
		eb.setSenderName(senderName);
		eb.setSubject(subject);
		eb.setBody(body);
		
		return eiEmailManager.sendEmail(eb).isSuccess();
	}

}
