package com.kadia.kblogserber.common;

public class MailHtml {
    public static String template(String text,String email){
        return "<div style=\"background:#e2f6f8;color:#666;padding: 20px;\">\n" +
                "    <div style=\"font-size: 35px;text-align: center;padding: 5px 0px;\">KBlog</div>\n" +
                "    <div style=\"font-size: 20px; padding: 5px 0px;\">"+text+"</div>\n" +
                "    <div style=\"font-size: 15px; color: #999;padding: 5px 0px;\">\n" +
                "        请勿回复，此邮箱为系统邮箱，有任何疑问请联系邮箱\n" +email+
                "    </div>\n" +
                "</div>\n";
    }
}
