package service.user.consumer.account;

import org.springframework.stereotype.Service;

/**
 * 账号的各种验证信息
 *
 * @author 5月11日 张易兴创建
 */
@Service(value = "VerificationService")
public class SecretProtectionService {
    /**
     * 用于判断用户是否设置密保
     *
     * @param mailbox 需要验证的邮箱
     * @return boolean 返回用户是否设置密保
     */
    public boolean isSecretProtection(String mailbox) {
        return false;
    }
}
