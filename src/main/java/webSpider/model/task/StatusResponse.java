package webSpider.model.task;

/**
 * Created by Yriy on 10/19/14.
 */
public enum StatusResponse {
    PROCESSING, FOUND, NOTFOUND, ERROR;

    @Override
    public String toString() {
        switch (this) {
            case PROCESSING:
                return "Processing";
            case FOUND:
                return "Found text";
            case NOTFOUND:
                return "Not found text";
            case ERROR:
                return "Error";
            default:
                throw new IllegalArgumentException();
        }
    }

    public static StatusResponse getStatus(int number) {
        StatusResponse status;
        switch (number) {
            case 0:
                status = StatusResponse.PROCESSING;
                break;
            case 1:
                status = StatusResponse.FOUND;
                break;
            case 2:
                status = StatusResponse.NOTFOUND;
                break;
            default:
                status = StatusResponse.ERROR;
        }
        return status;
    }
}
