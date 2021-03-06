package org.magic.api.notifiers.impl;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import org.magic.api.beans.MTGNotification;
import org.magic.api.beans.MTGNotification.FORMAT_NOTIFICATION;
import org.magic.api.beans.MTGNotification.MESSAGE_TYPE;
import org.magic.api.interfaces.abstracts.AbstractMTGNotifier;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

public class DiscordNotifier extends AbstractMTGNotifier {

	public static final int MAXSIZE=2000;
	
	public static void main(String[] args) throws IOException {
		new DiscordNotifier().send(new MTGNotification("Test", "<html>Test de :mana0: via apps</html>", MESSAGE_TYPE.INFO));
	}
	
	@Override
	public void send(MTGNotification notification) throws IOException {
		 
		JDA jda=null;
		try {
			
			jda = new JDABuilder(AccountType.BOT).setToken(getString("TOKEN")).buildBlocking();
			TextChannel chan = jda.getTextChannelById(getLong("CHANNELID"));
			notification.setSender(jda.getSelfUser().getName());
			
			StringBuilder msg = new StringBuilder();
			
			String emoji="";
			switch(notification.getType())
			{
				case ERROR : emoji=":error:";break;
				case WARNING: emoji=":warning:";break;
				case INFO : emoji=":information_source:";break;
				default : emoji="";
			}
			
			msg.append(emoji).append(notification.getMessage());
			msg.append("*").append(notification.getTitle()).append("*\n");
			
			String message=msg.toString();
			
			if(message.length()>MAXSIZE)
			{
				logger.error("Message is too long : " + msg.length() + ">"+MAXSIZE+". Will truncate it");
				message=message.substring(0, MAXSIZE);
			}
			
			logger.debug("send " + message);
			
			
			if(notification.getFile()==null)
				chan.sendMessage(message).queue();
			else
				chan.sendFile(notification.getFile(),msg.toString());
			
			
		} catch (LoginException e) {
			logger.error("couldn't init login",e);
		} catch (InterruptedException e) {
			logger.error("Interupted !",e);
			Thread.currentThread().interrupt();
		}
		finally {
			if(jda!=null)
				jda.shutdown();
		}
		 		 
	}

	@Override
	public FORMAT_NOTIFICATION getFormat() {
		return FORMAT_NOTIFICATION.MARKDOWN;
	}

	@Override
	public String getName() {
		return "Discord";
	}
	
	@Override
	public void initDefault() {
		setProperty("TOKEN","");
		setProperty("CHANNELID", "");
	}

}
