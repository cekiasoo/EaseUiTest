package com.ce.easeuitest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public class ContactFragment extends EaseContactListFragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new GetContact().execute();
    }
    private class GetContact extends AsyncTask<Void, Void, Map<String, EaseUser>> {
        @Override
        protected Map<String, EaseUser> doInBackground(Void... voids) {
            try {
                Map<String, EaseUser> contactMap = new HashMap<>();
                List<String> contacts = EMClient.getInstance().contactManager().getAllContactsFromServer();
                for (String contact : contacts) {
                    EaseUser easeUser = new EaseUser(contact);
                    contactMap.put(contact, easeUser);
                }
                return contactMap;
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Map<String, EaseUser> easeUserMap) {
            if (easeUserMap != null && !easeUserMap.isEmpty()) {
                setContactsMap(easeUserMap);
                refresh();
            }
        }
    }
}
