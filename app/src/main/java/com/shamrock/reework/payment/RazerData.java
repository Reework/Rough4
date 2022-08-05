package com.shamrock.reework.payment;

import com.shamrock.reework.razerpaypsyment.Customer;
import com.shamrock.reework.razerpaypsyment.Notes;
import com.shamrock.reework.razerpaypsyment.Notify;

public class RazerData {
    private String cancelled_at;

    private String[] reminders;

    private String amount_paid;

    private Notes notes;

    private String reference_id;

    private String created_at;

    private String description;

    private String expired_at;

    private Notify notify;

    private String short_url;

    private String callback_url;

    private String updated_at;

    private String upi_link;

    private String accept_partial;

    private String currency;

    private String id;

    private String callback_method;

    private String expire_by;

    private String first_min_partial_amount;

    private String amount;

    private String reminder_enable;

    private String user_id;

    private String order_id;

    private Customer customer;

    private String status;

    public String getCancelled_at() {
        return cancelled_at;
    }

    public void setCancelled_at(String cancelled_at) {
        this.cancelled_at = cancelled_at;
    }

    public String[] getReminders() {
        return reminders;
    }

    public void setReminders(String[] reminders) {
        this.reminders = reminders;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(String expired_at) {
        this.expired_at = expired_at;
    }

    public Notify getNotify() {
        return notify;
    }

    public void setNotify(Notify notify) {
        this.notify = notify;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpi_link() {
        return upi_link;
    }

    public void setUpi_link(String upi_link) {
        this.upi_link = upi_link;
    }

    public String getAccept_partial() {
        return accept_partial;
    }

    public void setAccept_partial(String accept_partial) {
        this.accept_partial = accept_partial;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCallback_method() {
        return callback_method;
    }

    public void setCallback_method(String callback_method) {
        this.callback_method = callback_method;
    }

    public String getExpire_by() {
        return expire_by;
    }

    public void setExpire_by(String expire_by) {
        this.expire_by = expire_by;
    }

    public String getFirst_min_partial_amount() {
        return first_min_partial_amount;
    }

    public void setFirst_min_partial_amount(String first_min_partial_amount) {
        this.first_min_partial_amount = first_min_partial_amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReminder_enable() {
        return reminder_enable;
    }

    public void setReminder_enable(String reminder_enable) {
        this.reminder_enable = reminder_enable;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
