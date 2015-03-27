package com.analysis.common.chunzhen;

/**
 * Created by crazyy on 15/3/25.
 */
public class ParserContext {

//    private IPMetadata ipMetadata;
    private boolean finish = false;

//    public ParserContext(IPMetadata ipMetadata);

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

//    public IPMetadata getIpMetadata() {
//        return ipMetadata;
//    }
//
//    public void setIpMetadata(IPMetadata ipMetadata) {
//        this.ipMetadata = ipMetadata;
//    }
}
