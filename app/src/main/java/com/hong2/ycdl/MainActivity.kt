package com.hong2.ycdl

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getHashKey()
    }

    private fun getHashKey(){
        try
        {
            val info = packageManager.getPackageInfo("com.hong2.ycdl", PackageManager.GET_SIGNATURES)
            for(signature in info.signatures){
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("myHash", "key_hash=${Base64.encodeToString(md.digest(), Base64.DEFAULT)}")
            }
        }catch (e: PackageManager.NameNotFoundException){
            e.printStackTrace()
        }
        catch (e: NoSuchAlgorithmException){
            e.printStackTrace()
        }
    }
}
