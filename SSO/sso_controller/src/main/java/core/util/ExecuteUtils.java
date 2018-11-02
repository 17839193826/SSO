package core.util;

import core.vo.R;

public class ExecuteUtils {
    public static R getR(int num, String msg) {
        if (num > 0) {
            return R.setOK(msg + "成功");
        } else {
            return R.setError(msg + "失败");
        }
    }
}
