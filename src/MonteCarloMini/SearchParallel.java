package MonteCarloMini;

import java.util.concurrent.RecursiveTask;

public class SearchParallel extends RecursiveTask<Minimum> {
	private static final long serialVersionUID = 1L;

	// arguments
	static final int seq_cut = 2000000;
	Search[] search;
	int numSearch, l, h;

	static final boolean DEBUG = false;

	public SearchParallel(Search[] searches, int low, int high) {
		search = searches;
		l = low;
		h = high;

	}

	@Override

	protected Minimum compute() { // this is a recursive task which returns min of type Minimum which has a
									// minimum and associated finder value

		if ((h - l) < seq_cut) {

			int min = Integer.MAX_VALUE;
			int local_min = Integer.MAX_VALUE;
			int finder = -1;

			for (int i = l; i < h; i++) {
				local_min = search[i].find_valleys();
				if ((!search[i].isStopped()) && (local_min < min)) { // don't look at those who stopped because hit
																		// exisiting path
					min = local_min;
					finder = i; // keep track of who found it
				}
				if (DEBUG)
					System.out.println("Search " + search[i].getID() + " finished at  " + local_min + " in "
							+ search[i].getSteps());
			}
			Minimum newMin = new Minimum(min, finder);
			return newMin;

		} else {
			SearchParallel left = new SearchParallel(search, l, (h + l) / 2);
			SearchParallel right = new SearchParallel(search, (h + l) / 2, h);
			left.fork();

			Minimum min1 = right.compute();

			Minimum min2 = left.join();

			// compares left and right to see which thread has a smaller minimum
			if (min2.getMin() < min1.getMin()) {
				return min2;
			} else {
				return min1;
			}

		}

	}

}