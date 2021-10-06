import re
def solution(word, pages):
    answer = 0
    base_score = dict()
    extern_score = dict()
    word = word.lower()
    page = []
    for i, ele in enumerate(pages):
        ele = ele.lower()
        # meta tag에서 주소 추출
        url = re.findall('<meta property="og:url" content="\S+"', ele)
        url = url[0].split('"')[3]
        page.append(url)

        # 외부링크 추출
        exter = re.findall('<a href="\S+"', ele)
        exter = [x.split('"')[1] for x in exter]

        # body tag 안에서만
        ele = ele.split()
        st, ed = -1, -1
        for j, el in enumerate(ele):
            if el == '<body>':
                st = j + 1
            elif el == '</body>':
                ed = j

        # 기본 점수 구하기
        val = 0
        for s in ele[st:ed]:
            for wo in re.split('[^a-z]', s):
                if not wo: continue
                if wo == word:
                    val += 1
        base_score[url] = val

        # 링크 점수 구하기
        if exter:
            ex_val = val / len(exter)
            for ex in exter:
                if ex in extern_score:
                    extern_score[ex] += ex_val
                else:
                    extern_score[ex] = ex_val
    scores = []
    for i, ele in enumerate(page):
        val = base_score[ele]
        sums = extern_score[ele] if ele in extern_score else 0
        scores.append((-val - sums, i))
    scores.sort()
    return scores[0][1]