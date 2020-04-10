package ftjw.web.mobile.analyze.exception;

import lombok.Getter;

/**
 * 殷晓龙
 * 2020/4/9 16:35
 */
@Getter
public class ParamException extends RuntimeException{
    private int code;
    private String msg;
    public ParamException(){
        this("请求参数错误");
    }

    public ParamException(String message) {
        this(444,message);

    }

    public ParamException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
