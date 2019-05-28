package test.zyx;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 类名：AlipayConfig
 * 功能：基础配置类
 * 详细：设置帐户有关信息及返回路径
 * 修改日期：2017-04-05
 * 说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号

    public static String app_id ="2016093000635007";

    // 商户私钥，您的PKCS8格式RSA2私钥

    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCJdWqe6d3CheyuehOIiM6yXfSTRahiQ8sWhwiEzsXi1N+Q+JVfsOExpGMg15tRyHZcHg52ujc4Nz7wuAEsbOzJeZz4xDw2K1z73mw2wASjrFTvmAQx9yvLHnUUkX6iClbPhb/wvxYj4XeQeTVoGLlDrZu8jZCpxKXRuRHnMB4/4F0i7NDaTqq1vD2mYr2LdKAEjkzg2TvuRh1w9Z5kybmPB7t8R2cYxVyrDOB8tj7OycAT8Pd+ST40IxSHDSMbJBAHAjH8cBcMjDV1MU5l0Nf/QJ9lQZmUroDYuUvxw0KJ2VPyE+SInxddg4iO3LGBwQKlpvY4ESYRGk6/uJB5BCKdAgMBAAECggEAIrkJXOlAvoGsHwrxYj0E0aPQQ1+GQIRVifREX6AH9tvFOc7BlPGOgUbAT6yHLKqR04U4LB+wEUBLYu1oRnuheOeCkoinmRttGqa9+TqaMWo37modLS393wprku2J6o7oDPYBXWQ3yIqM9G7Z4djU+9agD4pHL8tgA9FXR2otSf8WglZcyiInQHRIpVUOhfLCTvmcKzvEpEgVbptgQQ6EPT0aJET02cXNS15A/+QX6bjgbmzC+hgHv3Q0wFb7EA/fUbPgt9dmcC2nl1Fw+9xx6FbakKtFK8VOx5Af7YoFNiqiHioQDHqJ6t8qq1XqNhQS02jdrig4laaZe6Z9NnHV4QKBgQDv0V1s0q2XO5pYXa+gm+JGZ5UIFIgmjJ7D2wBprsOlaGzhRRajjopgtU6XZ+pr8NDyd5FPWIYrvcRZsfZPrldATgdY3A9CCHnh8/b/f4VQ/0MwgeN4peNtscnQr36TX2BIowUtXGeYgH3P6XR7hAQpWh69he6o3j3+W+ouS2htdQKBgQCSu+PN+pA2/NKQ19nSF4Z4U/il4Yya2Nn3fUvduLfMIAcPkyK0nyxqnWgd176TDTdpthYu2mxW1cAR7jDUVjF5Pb9SfTxBn5J6BmpRalzxZJWcqvWBHaP9YZ8QKcJgVF27JguSt3UcpczuOEGDM3glPqvROn6MQyAer5v1dF5ziQKBgQDjD8U4OwVXEnzhu9qE4/NLG9nUa7Q5Y50S7bEnXuwpGYU1iTeVJmnIhQHjguVGzBzZ5fG0QanPkn5tOn1vFi/QV9DHBpx/tgzIrjgn0zKD7RxNuj2IxvBdtR7TMsZSfNRqEcfeGgaQMuBsUasTI3zZNlWytjOwubPSuG4y5Pz73QKBgCVuRKhwuIUubVCBn5EWazkawp0eMk3173RQxB+lVU1/Bg4VDI9i42O3D649MOI2RuJyjqoZEfX8xOhOCKrB6hDxjXKve8WJvUXfiROHvHnfnMLAYaw0MXcbhgYLgQgtvVNWWzFsFzXdna9PLncXlBh8KjxPXaTOIs1xIqm0q7txAoGBAJwJGzC5LOa+uB+HPwLHnYVvJ4EKyMWUU51VlS4CWW53aca2KE55YwlErxSnKqR1WcblyU4Fy3d1SJLqrpmOL/+V6sQiN3XcVX0gACwYQ4hwQJfDvEShDL3+fXLPYfoiGDf4Z+WUWt8Vyt5WijTjMkIGwcbJZAxgnT14mOF8NLrr";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。

    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnE/17GJM+628oI+OJY9URpd6Beb6vvscbpN0WnFfXKjhYaCR9SdR+MbQ8vqCqUAqhn6VIDwSLIqYr3inSeI8Jov5kOmfOTeVfFIGamGEkzPscNxxw2+/Tpr8TuX2tpEjUtv7wNwUjZKFCStSsDO/gqX078RuV4lL91/mE5NyT2Hdm5X988ziMbauW4IYwXJrK4NmuWMI3LwBGPa2Q6FUtqxIJ+RQYsaaDUQK6lZcWAsi2bO4sY8rdy3CAMnpXWse0fPOpY6OqASfs3anHOgYArbxT+krG2qXaB/2eHc9bWuMDsCpbWGoEw7OHZRq4alJWnWMA9aipFHNyGyYkjIYrwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问

    public static String notify_url = "http://10.1.36.102/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问

    public static String return_url = "http://localhost:8080/FutureMusic_war_exploded/index";

    // 签名方式

    public static String sign_type = "RSA2";

    // 字符编码格式

    public static String charset = "utf-8";

    // 支付宝网关

    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关

    public static String log_path = "F:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


