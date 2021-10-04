import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 프로그래머스 ID: 42893, 매칭 점수 */
public class Solution {
    private static final Pattern A_LINK_PATTERN = Pattern.compile("<a href=\"(.*?)\"/?>");
    private static final Pattern BODY_PATTERN = Pattern.compile("<body>(.*)</body>");
    private static final Pattern META_PATTERN = Pattern.compile("<meta property=\"og:url\" content=\"(.*)\"/>");

    private static final String TAG_REGEX = "<.+?>";
    private static final String ENTER_REGEX = "[\\n]";
    private static final String NOT_ALPHABET_REGEX = "[^a-z]";

    static class HtmlParser {
        private List<String> matches(Pattern pattern, String str) {
            Matcher matcher = pattern.matcher(str);

            List<String> matched = new ArrayList<>();
            while (matcher.find()) {
                matched.add(matcher.group(1));
            }
            return matched;
        }

        public List<String> aLinks(String html) {
            return matches(A_LINK_PATTERN, html);
        }

        public long count(String html, String word) {
            String body = matches(BODY_PATTERN, html.replaceAll(ENTER_REGEX, " ")).get(0);
            body = body.replaceAll(TAG_REGEX, " ");

            String[] words = body.split(NOT_ALPHABET_REGEX);
            long count = 0;
            for (String w : words) {
                if (word.equals(w)) {
                    count++;
                }
            }
            return count;
        }

        public String url(String html) {
            return matches(META_PATTERN, html).get(0);
        }
    }

    static class Page {
        int index;
        long count;
        List<Integer> fromOut;
        List<String> outLink;

        public Page(int index, long count) {
            this.index = index;
            this.count = count;
            this.fromOut = new ArrayList<>();
            this.outLink = new ArrayList<>();
        }

        private void addFromOut(int index) {
            if (!fromOut.contains(index)) {
                fromOut.add(index);
            }
        }

        private void addOutLink(String url) {
            if (!outLink.contains(url)) {
                outLink.add(url);
            }
        }
    }

    public double getScore(List<Page> pages, int index) {
        Page page = pages.get(index);
        double basic = page.count;

        double outLink = 0;
        for (int out : page.fromOut) {
            Page outPage = pages.get(out);

            double outPageOutLinkCount = outPage.outLink.size();
            if (outPageOutLinkCount == 0) {
                continue;
            }
            outLink += outPage.count / outPageOutLinkCount;
        }
        return basic + outLink;
    }

    public int solution(String word, String[] pages) {
        word = word.toLowerCase();
        HtmlParser parser = new HtmlParser();

        // get url of page
        List<String> index = new ArrayList<>();
        List<Page> pageList = new ArrayList<>();
        for (int i = 0; i < pages.length; i++) {
            String page = pages[i].toLowerCase();
            index.add(parser.url(page));
            pageList.add(new Page(i, parser.count(page, word)));
        }

        // init page information
        for (int from = 0; from < pages.length; from++) {
            String page = pages[from].toLowerCase();
            for (String link : parser.aLinks(page)) {
                pageList.get(from).addOutLink(link);
                int to = index.indexOf(link);
                if (to >= 0) {
                    pageList.get(to).addFromOut(from);
                }
            }
        }

        // calculate score
        double maxScore = 0;
        int maxScoreIndex = 0;
        for (int i = 0; i < pageList.size(); i++) {
            double score = getScore(pageList, i);
            if (score > maxScore) {
                maxScore = score;
                maxScoreIndex = i;
            }
        }
        return maxScoreIndex;
    }

    public static void main(String[] args) {
        String[] pages = new String[] {"<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://careers.kakao.com/interview/list\"/>\n</head>  \n<body>\n<a href=\"https://programmers.co.kr/learn/courses/4673\"></a>#!MuziMuzi!)jayg07con&&\n\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://www.kakaocorp.com\"/>\n</head>  \n<body>\ncon%\tmuzI92apeach&2<a href=\"https://hashcode.co.kr/tos\"></a>\n\n\t^\n</body>\n</html>"};
        String word = "muzI";
        int index = new Solution().solution(word, pages);
        System.out.println(index);
    }
}
