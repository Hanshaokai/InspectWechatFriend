package com.example.han.inspectwechatfriend;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.han.inspectwechatfriend.accessibility.InspectWechatFriendService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ${han} on 2016/10/27.
 */

public class Preferences {

    public static void saveDeleteFriends(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("delete", Context.MODE_PRIVATE);
        sharedPreferences.edit().putStringSet("delete_friends", InspectWechatFriendService.deleteList).apply();
    }

    public static List<String> getDeleteFriends(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("delete", Context.MODE_PRIVATE);
        Set<String> hashSet = sharedPreferences.getStringSet("delete_friends", new HashSet<String>());
        List<String> stringList = new ArrayList<>();
        for (String s : hashSet) {
            stringList.add(s);
        }
        return stringList;

    }
}
