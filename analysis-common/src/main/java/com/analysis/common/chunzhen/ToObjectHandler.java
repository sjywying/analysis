package com.analysis.common.chunzhen;

/**
 * Created by crazyy on 15/3/25.
 */
public class ToObjectHandler implements Handler {


    @Override
    public Object execute(final ParserContext context, Object value) {
        String line = (String) value;

        String[] iparr = new String[4];

        int i = 0;
        String[] temparr = line.split(" ");
        for (String str : temparr) {
            if(str != null && !str.isEmpty()) {
                iparr[i] = str;
                i++;
            }

            if(i >= 4) {
                break;
            }
        }

        if(iparr[0] == null || iparr[1] == null || iparr[0].indexOf('.') == -1 || iparr[1].indexOf('.') == -1) {
            return null;
        }

        IPMetadata ipMetadata = new IPMetadata();
        ipMetadata.setStartIp(iparr[0]);
        ipMetadata.setEndIp(iparr[1]);
        ipMetadata.setAddress(iparr[2]);
        ipMetadata.setNetOperator(iparr[3]);

//        if(ipMetadata.getAddress() == null || "".equals(ipMetadata.getAddress().trim())) return null;

        return ipMetadata;
    }

}
