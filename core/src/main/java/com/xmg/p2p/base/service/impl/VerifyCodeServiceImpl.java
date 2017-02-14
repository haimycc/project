package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.DateUtil;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Panda on 2016/10/31.
 */
@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService {
    @Value("${sms.url}")
    private String url;
    @Value("${sms.username}")
    private String username;
    @Value("${sms.password}")
    private String password;
    @Value("${sms.apikey}")
    private String apikey;

    /**
     * 发送邮箱验证码
     *
     * @param phoneNumber
     */
    public void sendVerifyCode(String phoneNumber) {
        //判断是否能够发送验证码
        //从session中得到VerifyCodeVO对象
        VerifyCodeVO vo = UserContext.getVerifyCode();
        if (vo == null //没有发送过
                || (vo != null && DateUtil.getBetweenSeconds(new Date(), vo.getSendTime()) >= BidConst.SEND_VERIFYCODE_INTERCAL)) {//发送时间小于90s
            //生成验证码
            String randomCode = UUID.randomUUID().toString().substring(0, 4);
            try {
                //发送短信
                //短信网关
                //post的请求地址
                URL postUrl = new URL(url);
                //打开连接,转换成HttpURLConnection
                HttpURLConnection conn = (HttpURLConnection) postUrl.openConnection();
                //设置是否向connection输出,post请求,参数放在http正文设置true
                conn.setDoOutput(true);
                //设置响应头
                //conn.setRequestProperty("Content-Type",   "multipart/form-data; charset=UTF-8; ");
                //设置请求方式,一定大写
                conn.setRequestMethod("POST");
                //拼接请求内容
                StringBuilder sb = new StringBuilder(100).append("username=").append(username).append("&password=").append(password)
                        .append("&apikey=").append(apikey).append("mobile").append(phoneNumber).append("&content=").append("发送验证码为").append(randomCode).append(",有效时间")
                        .append(BidConst.VERIFYCODE_VALID_TIME).append("分钟,该手机号码为").append("&encode=1");
                //获取输出流,并输出
                conn.getOutputStream().write(sb.toString().getBytes());
                //发送请求并响应(String) 把InputStream → String(使用Spring工具类),并且设置字符编码
                String response = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("UTF-8"));
                //再判断响应参数是否是success开头
                if (response.startsWith("success")) {
                    //构造VerifyCodeVo
                    vo = new VerifyCodeVO();
                    vo.setPhoneNumber(phoneNumber);
                    vo.setSendTime(new Date());
                    vo.setVerifyCode(randomCode);
                    UserContext.setVerifyCode(vo);
                } else {
                    //**发送失败,抛出异常信息,但try-catch同一抛出,"发送短信失败,请稍后再尝试!",另外可将发送失败信息记录存储到数据以及传递对应的运营人员或管理员
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("发送短信失败,请稍后再尝试!");
            }
        } else {
            throw new RuntimeException("发送过于频繁");
        }
    }

    /**
     * 手机绑定验证
     *
     * @param phoneNumber
     * @param verifyCode
     * @return
     */
    public boolean vaild(String phoneNumber, String verifyCode) {
        VerifyCodeVO vo = UserContext.getVerifyCode();
        System.out.println(vo.getSendTime());
        System.out.println(DateUtil.getBetweenSeconds(new Date(), vo.getSendTime()) <= BidConst.VERIFYCODE_VALID_TIME);
        return (vo != null
                && vo.getVerifyCode().equalsIgnoreCase(verifyCode)//验证码相同
                && vo.getPhoneNumber().equals(phoneNumber)//手机号相同
                && DateUtil.getBetweenSeconds(new Date(), vo.getSendTime()) <= BidConst.VERIFYCODE_VALID_TIME);//验证码没有过期
    }
}
