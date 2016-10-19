package us.guihouse.autobank.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import us.guihouse.autobank.bean.Bill;
import us.guihouse.autobank.bean.Login;

/**
 * Created by aluno on 18/10/16.
 */

public class ListBillsRequest extends AuthenticatedRequest<List<Bill>> {

    @Override
    protected CharSequence getUploadData() {
        return null;
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return null;
    }

    @Override
    protected List<Bill> convertResponse(String responseBody) {
        return null;
    }
}
