/*
 * This file is part of eloop.
 *
 * eloop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * eloop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with eloop. If not, see <http://www.gnu.org/licenses/>.
 */

package me.mneri.ca.app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ApplicationLoop {
    private final BlockingQueue<Runnable> mTasks = new LinkedBlockingQueue<>(); // The queue of tasks.
    private volatile boolean mRunning; // true if the loop is running.
    private boolean mStarted; // true if the loop has already been started.
    private final Thread mThread = new Thread(new Runnable() { // This is the event loop thread.
        @Override
        public void run() {
            while (mRunning) {
                try {
                    mTasks.take().run();
                } catch (InterruptedException ignored) { }
            }
        }
    });

    void enqueue(Runnable runnable) {
        if (!mRunning)
            throw new IllegalLoopStateException();

        mTasks.add(runnable);
    }

    void quit() {
        enqueue(() -> mRunning = false);
    }

    void start() {
        if (mStarted)
            throw new IllegalLoopStateException();

        mStarted = true;
        mRunning = true;
        mThread.start();
    }
}
