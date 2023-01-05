# KWork Module

KWork Module used as a solution for offloading "work" or "tasks" that can run independently of the
application in the background.

### Notes :

Main api surface consists of is a thin wrapper for `androidx.work.WorkManager`

- `KWorkManager`
  This is the main api surface for add, removing, cleaning work.

- `Kworker`
  This module includes `Kworker`, this class should be extended by the client.

Under the hood this implementation relies on `WorkManager`. So implementations for refer to the
[documentation for WorkManager] on how to setup work manager.

[documentation for WorkManager]: https://developer.android.com/guide/background/persistent