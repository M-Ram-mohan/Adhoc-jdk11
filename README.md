# 🧪 MongoDB Read/Write Performance Testing

This repository contains performance benchmarking of different MongoDB operations for inserts, reads, and upserts. The goal is to determine the most efficient way to handle bulk data operations under various batch sizes.

## 🧾 Test Notes

- All tests were measured using Java's `StopWatch` and the time is presented in **seconds**.
- **Insert size** = Number of documents inserted.
- **Batch size** = Number of documents per batch.
- **Upsert size** = Same as insert size.
- All operations were executed in isolation per test.

---

## 📊 Summary of MongoDB Operation Timings

| Insert Size | Batch Size | Parallel Insert (s) | Paginated Reads (s) | Cursor Reads (s) | Sequential Upsert (s) | Parallel Upsert (s) | Total Time (s) |
|-------------|------------|----------------------|----------------------|-------------------|------------------------|----------------------|----------------|
| 1000        | 100        | 4.52                 | 0.43                 | 0.16              | 9.48                   | 5.00                 | 19.59          |
| 1000        | 500        | 1.89                 | 1.16                 | 0.10              | 5.04                   | 4.02                 | 12.20          |
| 10000       | 2000       | 6.68                 | 1.12                 | 0.77              | 27.89                  | 8.77                 | 45.24          |
| 10000       | 5000       | 6.93                 | 0.86                 | 0.95              | 15.54                  | 12.84                | 37.13          |
| 20000       | 2000       | 9.02                 | 1.99                 | 1.65              | 54.77                  | 5.88                 | 73.30          |
| 20000       | 5000       | 2.06                 | 7.64                 | 0.95              | 35.03                  | 14.73                | 60.40          |
| 50000       | 2000       | 10.04                | 4.59                 | 2.74              | 124.28                 | 14.27                | 155.91         |
| 50000       | 5000       | 5.18                 | 11.09                | 4.18              | 82.17                  | 19.73                | 122.34         |

---

## 📌 Observations

- **Sequential upserts** are consistently the most time-consuming operation, especially as the data size increases.
- **Parallel upserts** offer better scalability at higher batch sizes, but not always linearly.
- **Batch size** has a noticeable impact on performance. A batch size of 5000 shows reduced insert and upsert times in many cases.
- **Reads (Paginated & Cursor)** make up a small portion of total execution time, with cursor reads being consistently faster.

---

## 🔧 Future Improvements

- Run tests with MongoDB transactions and compare impact.
- Test with sharded and replicated clusters.
- Include memory and CPU profiling under load.

