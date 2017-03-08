/*** Auto generator by codegenerator 2014-06-20 16:55:03*/
package com.aspire.webbas.portal.common.entity;

public class PendTaskSetting {
	public static final String YES = "1";
	public static final String NO = "0";
    /**
    员工ID
     */
    private String staffid;

    /**
    Email发送标志,1发送 0不发
     */
    private String sendemail = "";

    /**
    Sms发送标志,1发送 0不发
     */
    private String sendsms = "";

    /**
    Email发送时间,格式：N个HH，并且每个HH后要带逗号, 如08,09,
     */
    private String emailsendtime = "";

    /**
    Sms发送时间,格式：N个HH，如0809
     */
    private String smssendtime = "";

    /**
    Email发送方式,1合并待办发送 0不合并待办发送 缺省为不合并，每条待办一封邮件
     */
    private String emailsendtype = "";

    /**
    员工ID
     * @return the value of PEND_TASK_SETTING.STAFFID
     */
    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid == null ? null : staffid.trim();
    }

    /**
    Email发送标志,1发送 0不发
     * @return the value of PEND_TASK_SETTING.SENDEMAIL
     */
    public String getSendemail() {
        return sendemail;
    }

    public void setSendemail(String sendemail) {
        this.sendemail = sendemail == null ? null : sendemail.trim();
    }

    /**
    Sms发送标志,1发送 0不发
     * @return the value of PEND_TASK_SETTING.SENDSMS
     */
    public String getSendsms() {
        return sendsms;
    }

    public void setSendsms(String sendsms) {
        this.sendsms = sendsms == null ? null : sendsms.trim();
    }

    /**
    Email发送时间,格式：N个HH，并且每个HH后要带逗号, 如08,09,
     * @return the value of PEND_TASK_SETTING.EMAILSENDTIME
     */
    public String getEmailsendtime() {
        return emailsendtime;
    }

    public void setEmailsendtime(String emailsendtime) {
        this.emailsendtime = emailsendtime == null ? null : emailsendtime.trim();
    }

    /**
    Sms发送时间,格式：N个HH，如0809
     * @return the value of PEND_TASK_SETTING.SMSSENDTIME
     */
    public String getSmssendtime() {
        return smssendtime;
    }

    public void setSmssendtime(String smssendtime) {
        this.smssendtime = smssendtime == null ? null : smssendtime.trim();
    }

    /**
    Email发送方式,1合并待办发送 0不合并待办发送 缺省为不合并，每条待办一封邮件
     * @return the value of PEND_TASK_SETTING.EMAILSENDTYPE
     */
    public String getEmailsendtype() {
        return emailsendtype;
    }

    public void setEmailsendtype(String emailsendtype) {
        this.emailsendtype = emailsendtype == null ? null : emailsendtype.trim();
    }
}
