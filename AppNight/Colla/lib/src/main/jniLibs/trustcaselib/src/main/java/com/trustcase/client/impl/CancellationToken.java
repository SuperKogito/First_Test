package com.trustcase.client.impl;

/**
 * Propagates notification that an ongoing file download should be canceled.
 *
 * Passing a CancellationToken to library routines allows caller code to "remotely" control TrustCaseClient
 * by flipping the boolean flag.
 *
 * Implementations of TrustCaseClient are responsible for checking whether the flag has not been switched
 * while the download is in progress and kill the operation as soon as value change is detected.
 */
public final class CancellationToken {
    private boolean isCancelled = false;

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled() {
        this.isCancelled = true;
    }

    @Override
    public String toString() {
        return "CancellationToken{" + "isCancelled=" + isCancelled + '}';
    }
}