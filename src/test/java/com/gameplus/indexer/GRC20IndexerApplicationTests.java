package com.gameplus.indexer;

import com.gameplus.indexer.utils.HttpUtil;
import com.gameplus.indexer.utils.NonceUtil;
import com.gameplus.indexer.utils.SigUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GRC20IndexerApplicationTests {

    String privateKey = "";
    String pubkey = "";

    @Test
    void contextLoads() {
    }

    @Test
    void testSigUtil() {
        String msg = "Hello";
        String sig = SigUtil.signMessage(privateKey, msg);
        boolean verify = SigUtil.verifySig(pubkey, msg, sig);
        assertTrue(verify, "Sig verify fail");
    }

    @Test
    void testNonce() {
        long nonce0 = NonceUtil.get("GPD");
        long nonce1 = NonceUtil.get("GPD");
        long nonce2 = NonceUtil.get("GPD");
        assertEquals(nonce0, 0, "First nonce not 0");
        assertEquals(nonce1, 1, "Second nonce not 1");
        assertEquals(nonce2, 2, "Third nonce not 2");
    }

    @Test
    void metaInfoTest() {
        String metaUri = "https://dev.gameplus.ai/api/gpd/LTP0C8";
        String metaData = HttpUtil.get(metaUri);
        Assert.hasLength(metaData, "Meta data not has Length");
    }


}
