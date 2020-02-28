package com.open.baselibrary.managers.sms;

import com.open.baselibrary.managers.IManager;

public interface ISmsManager extends IManager {
    //region Events
    class SmsReceivedEvent {
        private String from;
        private String message;

        public String getFrom() {
            return from;
        }

        public String getMessage() {
            return message;
        }

        public SmsReceivedEvent(String from, String message) {
            this.from = from;
            this.message = message;
        }
    }
    //endregion
}
