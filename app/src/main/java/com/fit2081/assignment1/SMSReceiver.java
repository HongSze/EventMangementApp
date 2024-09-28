package com.fit2081.assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    public static final String SMS_FILTER = "SMS_FILTER";
    public static final String SMS_MSG_KEY = "SMS_MSG_KEY";
    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages= Telephony.Sms.Intents.getMessagesFromIntent(intent);
        if (messages != null && messages.length > 0) {
            String messageBody = messages[0].getMessageBody();

            /*
             Sends a broadcast which contains the new message to MainActivity
             * */
            Intent msgIntent = new Intent();
            msgIntent.setAction(SMS_FILTER);
            msgIntent.putExtra(SMS_MSG_KEY, messageBody);
            context.sendBroadcast(msgIntent);
        }
    }
}