package specification.service;

public class Range {
    private long start;
    private long end;

    public Range(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public boolean isInvalid(long fileSize) {
        return start >= fileSize || end >= fileSize || start < 0 || end < 0 || start > end;
    }

    public boolean isEmpty() {
        return start == end;
    }

    public String toContentRangeHeaderValue(long fileSize) {
        return "bytes " + start + "-" + end + "/" + fileSize;
    }

    public static Range parse(String rangeHeader, long fileSize) {
        if (rangeHeader == null || rangeHeader.isBlank() || !rangeHeader.startsWith("bytes=")) {
            return null;
        }

        String[] rangeValues = rangeHeader.substring(6).split("-");
        if (rangeValues.length != 2) {
            return null;
        }

        try {
            long start = Long.parseLong(rangeValues[0]);
            long end = rangeValues[1].isEmpty() ? fileSize - 1 : Long.parseLong(rangeValues[1]);
            return new Range(start, end);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}