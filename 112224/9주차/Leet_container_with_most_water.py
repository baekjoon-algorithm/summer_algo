# https://leetcode.com/problems/container-with-most-water/
class Solution:
    def maxArea(self, height: List[int]) -> int:
        ret = 0
        l, r = 0, len(height) - 1
        while l < r:
            h = min(height[l], height[r])
            ret = max(ret, h * (r - l))
            if height[l] <= h: l += 1
            if height[r] <= h: r -= 1

        return ret