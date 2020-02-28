package com.open.baselibrary.managers.sms;

import com.open.baselibrary.managers.BaseManager;

import org.greenrobot.eventbus.EventBus;

public class SmsManager extends BaseManager implements ISmsManager {
	private static byte[] key = new byte[]{'c', 'o', 'm', '.', 'o', 'p', 'e', 'n', '.', 'w', 'a', 'n', 'a', 'n', 'd', 'r', 'o', 'i', 'd', '.', 'v', 'i', 'e', 'w', '.', 'm', 'a', 'i', 'n', '.', 'p', 'r', 'e', 's', 'e', 'n', 't', 'e', 'r', '.', 'M', 'a', 'i', 'n', 'A', 'c', 't', 'i', 'v', 'i', 't', 'y', 'P', 'r', 'e', 's', 'e', 'n', 't', 'e', 'r'};
    private static SmsManager ourInstance = new SmsManager();

    private SmsManager() {
    }

    public static ISmsManager getInstance() {
        return ourInstance;
    }

    static SmsManager getInternalInstance() {
        return ourInstance;
    }

    public void onSmsReceived(String from, String message) {
        EventBus.getDefault().post(new SmsReceivedEvent(from, message));
    }

    private static byte[] encryptDecrypt(byte[] input) {
        byte[] output = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            output[i] = (byte) (input[i] ^ key[i % key.length]);
        }

        return output;
    }

    public static byte[] getSettingsPassword() {
        byte[] key = {0x52, 0x5a, 0x0f, 0x18, 0x42, 0x0d, 0x4e, 0x56, 0x02, 0x4a, 0x44, 0x17, 0x10,
                0x41, 0x46, 0x06, 0x5a, 0x00, 0x40, 0x48, 0x02, 0x44, 0x0a, 0x1b, 0x43, 0x5f, 0x55,
                0x4e, 0x1b, 0x08, 0x54, 0x50, 0x5d, 0x1e, 0x47, 0x4b, 0x56, 0x11, 0x56, 0x5b, 0x42,
                0x03, 0x45, 0x1c, 0x2c, 0x05, 0x51, 0x5d};
        return encryptDecrypt(key);
    }
}
