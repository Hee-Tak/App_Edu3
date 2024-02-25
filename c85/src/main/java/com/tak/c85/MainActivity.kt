package com.tak.c85

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val resultView = findViewById<TextView>(R.id.resultView)
        resultView.text = isNetworkAvailable()
    }

    private fun isNetworkAvailable() : String {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ //마시멜로
            val nw = manager.activeNetwork ?: return "offline"      //null 이면 offline 상태
            val actNw = manager.getNetworkCapabilities(nw) ?: return "offline"

            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return "wifi online"
                }
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return "cellular online"
                }
                else -> "offline"
            }
        } else {
            if(manager.activeNetworkInfo.isConnected ?: false){
                return "online"
            } else {
                return "offline"
            }
        }
    }
}

/**
 * 파트23 - 네트워크 프로그래밍으로 서버와 데이터를 주고 받자.
 *
 * C84) Concepts of Network Programming
 *
 *
 * * 대부분의 응용 프로그램에서는 서버랑 네트워킹을 하는 것 같다.
 *      안드로이드가 프론트엔드 어플리케이션이 되겠구나.
 *      업무 로직이 거의 대부분 서버 쪽의 백엔드 어플리케이션에서 진행을 하다 보니까
 *      적절하게 서버랑 네트워킹을 통해서 데이터를 주고받는 프로그램은, 거의 대부분의 응용 프로그램에서 필수가 아닐까 싶다.
 *
 * * 서버와 네트워킹 하는 방식은 여러 가지가 있을 텐데, 가장 기본적인 것은 http 통신이 되겠다.
 *
 * * 서버와 네트워킹을 하기 위해서는 퍼미션이 필요하다.
 *
 * <Network Programming>
 *
 * - 퍼미션 필요
 *      <uses-permission android:name="android.permission.INTERNET"/>
 *
 *
 *
 * * 서버와 Http 통신을 하기 위한 방법이 여러가지가 있다.
 *
 * - HttpURLConnection
 * - java SE 에서 제공되는 API.
 * - 표준 라이브러리. 초기 버전부터 제공.
 *
 * * 그래서 표준 API 에서 제공하는 자바 클래스를 이용해서 직접 네트워크 프로그램을 작성하려면 할 수도 있다. 그런데, 그렇게되면 프로그램 코드가 좀 길어지거나, 복잡해지는 측면이 있다. 중복되는 코드도 발생할 수도 있고 (가능하긴 하다만)
 * * 그래서 대부분은 이것을 추상화 시킨 별도의 어떤 라이브러리를 가지고 개발하는 것이 일반적이다.
 *
 *
 * - HttpClient
 * - apache http 라이브러리.     (아파치의 라이브러리긴 한데, 원래는 안드로이드 표준에 있었다.)
 * - 안드로이드 초기 HttpURLConnection 부분에 내부적인 문제가 있어서, 표준 라이브러리에 포함되어 많이 사용하던 라이브러리. (apache)
 * - Android 6.0 에서는 Apache HTTP 클라이언트에 대한 지원이 제거.
 * - Android 9 부터는 이 라이브러리가 bootclasspath 에서 제거되고 기본적으로 앱에서 사용할 수 없다.
 * - Android 9 이상을 대상으로 하는 앱이 Apache HTTP 클라이언트를 계속 사용하려면 AndroidManifest.xml에 추가해야 한다. (uses-library)
 *
 *
 *
 *
 * - volley
 * - 2013년 Google I/O 에서 발표한 라이브러리이다.
 * - http 통신을 위한 다양한 기능을 제공해주는 라이브러리이다.     (좀 더 간결하게, 중복되는 코드를 신경쓸 거 없이 프로그램을 작성할 수 있다.)
 * - 1.0.0 버전에서는 내부적으로 apache http 라이브러리를 이용했으며 1.1.1 버전에서는 apache http 종속성이 제거된 라이브러리이다. (구글의 라이브러리지만 별도의 API다 보니까, dependency 관계 설정하고 써야함.)
 *
 *
 * - okHttp
 * - square 라는 회사에서 만든 라이브러리이다.
 *
 *
 * - retrofit               (현시점 안드로이드 개발자에게 가장 유명한 라이브러리) (http 통신을 하기 위해서 이용하는 라이브러리 중에서 가장 이용 비율이 높고, 가장 유명한 Retrofit)
 * - 2013년 1.0 발표되었고, 2016년 2.0 발표된 라이브러리이다.    (얘도 square 회사에서 만든 것)       (2.0 나오면서 흔히 Retrofit2 라고 부름) (근데 근래에는 뭐 그냥 Retrofit 이라 하면 이 2.0 을 얘기하는 것)
 * - 내부적으로 okHttp를 이용한다.
 *
 *
 *
 *
 *
 *
 * - api level 28 부터 네트웍 보안 정책변경.           (http 프로토콜이 허용안되게 변경됨)
 * - https 는 문제 없으며 http 인 경우는 설정이 필요 -> (첫번째 방법은 Manifest 파일에 usesCleartextTraffic 속성을 설정해주기. true 값으로.) (두번째 방법은 xml 폴더에, 개발자 임의의 xml을 만들고 network_security_config 이용)
 *
 *
 * - usesCleartextTraffic 설정
 * - 이전에도 있었던 속성. api level 28 부터는 기본 값이 false 로 변경. 명시적으로 true 로 설정해 주어야 한다.
 *              <application
 *
 *                  android:usesCleartextTraffic="true">        => 이 어플리케이션에 http 통신을 허용하라 이런설정임.
 *
 * - network_security_config 이용
 *              <network-security-config>
 *                  <domain-config cleartextTrafficPermitted="true">
 *                      <domain includeSubdomains="true">192.168.1.2</domain>               -> http 통신이 허용되어야되는 도메인 혹은 ip 어드레스를 등록하는 것. (그러면 여기에다가 또 여러개를 등록할 수 있다.) (여기에 해당되는 서버랑만 http를 허용하겠다. 뭐 그런 얘끼)
 *                  </domain-config>
 *              </network-security-config>
 *
 *
 *              <application
 *
 *                  android:networkSecurityConfig="@xml/network_security_config">           -> manifest 에다가 xml 정보를 주는 것. 이렇게 주면 특정 도메인에 해당되는 서버랑 http 통신이 허용이 된다.
 *
 *
 */


/**
 * 85강) Network 정보 확인
 *
 * * 우리가 서버랑 네트워킹을 하기 위해서는 실제 서버에 커넥션을 맺고 데이터를 read/write 하는 프로그램이 당연히 필요하겠다만, 일반적으로는 서버랑 네트워킹을 시도하기 전에, 핸드폰의 네트웍 상태를 파악하는 것이 선행되어야 한다.
 *
 *
 * <ConnectivityManager>
 *
 * - ConnectivityManager 을 이용해 네트웍 상태를 파악       (시스템 서비스)
 *          <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 *
 *
 * - ConnectivityManger 의 getActiveNetwork() 을 이용해 Network 객체 획득    (네트웍 정보가 담기는 객체)
 * - Network 객체를 getNetworkCapabilities() 함수의 매개변수로 지정하면 현재 접속된 네트웍 망 정보를 획득가능
 *
 *          val nw = connectivityManager.activeNetwork
 *          val actNw = connectivityManager.getNetworkCapabilities(nw)
 *
 *
 * - hasTransport() 함수를 이용해 현재 폰에 와이파이에 접속된 상태인지 아니면 이통사망에 접속된 상태인지를 파악
 *
 *          actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
 *              return "wifi online"
 *          }
 *          actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
 *              return "cellular available"
 *          }
 *          //위에도 안걸리고, 밑에도 안걸리면 현재 데이터 통신을 위한 네트워킹이 안되는 상태라는 말 => Offline 상태다.
 *
 *
 *
 */