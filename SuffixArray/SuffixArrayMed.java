public class SuffixArrayMed extends suffixarray {

    // Wrapper class to help sort suffix ranks
    static class SuffixRankTuple implements Comparable<SuffixRankTuple> {
        int firsthalf, secondhalf, originalIndex;

        // Sort Suffix ranks first on the first half then the second half
        @Override
        public int compareTo(SuffixRankTuple other) {
            int cmp = Integer.compare(firsthalf, other.secondhalf);
            if (cmp == 0)
                return Integer.compare(secondhalf, other.secondhalf);
            return cmp;
        }

        @Override
        public String toString() {
            return originalIndex + " -> (" + firsthalf + ", " + secondhalf + ")";
        }
    }

    public SuffixArrayMed(String text) {
        super(toIntArray(text));
    }

    public SuffixArrayMed(int[] text) {
        super(text);
    }

    // Construct a suffix array in O(nlog^2(n))
    @Override
    protected void construct() {
        sa = new int[N];

        // Maintain suffix ranks in both a matrix with two containing the current and
        // last rank information as well as some sortable rank objects
        int[][] suffixRanks = new int[2][N];
        SuffixRankTuple[] ranks = new SuffixRankTuple[N];

        // Assign a numerical value to each character in the text
        for (int pos = 1; pos < N; pos *= 2) {
            for (int i = 0; i < N; i++) {
                SuffixRankTuple suffixRank = ranks[i];
                suffixRank.firsthalf = suffixRanks[0][i];
                suffixRank.secondhalf = i + pos < N ? suffixRanks[0][i + pos] : -1;
                suffixRank.originalIndex = i;
            }

            // O(nlog(n))
            java.util.Arrays.sort(ranks);

            int newRank = 0;
            suffixRanks[1][ranks[0].originalIndex] = 0;

            for (int i = 1; i < N; i++) {
                SuffixRankTuple lastSuffixRank = ranks[i - 1];
                SuffixRankTuple currSuffixRank = ranks[i];
                // If the first half differs from the second half
                if (currSuffixRank.firsthalf != lastSuffixRank.firsthalf
                        || currSuffixRank.secondhalf != lastSuffixRank.secondhalf)
                    newRank++;
                suffixRanks[1][currSuffixRank.originalIndex] = newRank;
            }

            // Place top row(current row) to be the last row
            suffixRanks[0] = suffixRanks[1];

            // Optimization to stop early
            if (newRank == N - 1)
                break;
        }

        // Fill suffix array
        for(int i=0;i<N;i++){
            sa[i] = ranks[i].originalIndex;
            ranks[i] = null;  
        }

        //Cleanup
        suffixRanks[0] = suffixRanks[1] = null;
        suffixRanks = null;
        ranks = null;
    }
    public static void main(String[] args){
        SuffixArrayMed sa = new SuffixArrayMed("ABBABAABAA");
        System.out.println(sa);
    }
}