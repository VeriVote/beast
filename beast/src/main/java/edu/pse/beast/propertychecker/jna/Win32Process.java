package edu.pse.beast.propertychecker.jna;

import java.io.IOException;

//thanks for the code to: http://stackoverflow.com/a/10124625

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.WinNT;

/**
 * The Class Win32Process.
 */
public class Win32Process {

    /** The Constant SYNCHRONIZE. */
    private static final int SYNCHRONIZE = 0x00100000;

    /** The Constant PROCESS_TERMINATE. */
    private static final int PROCESS_TERMINATE = 0x0001;

    /** The Constant PROCESS_SUSPEND_RESUME. */
    private static final int PROCESS_SUSPEND_RESUME = 0x0800;

    /** The Constant PROCESS_QUERY_INFORMATION. */
    private static final int PROCESS_QUERY_INFORMATION = 0x0400;

    /**
     * The windows new technology handle for this process.
     */
    private WinNT.HANDLE handle;

    /**
     * The process id for this process.
     */
    private int pid;

    /**
     * Creates a new Win32 Process given a process id.
     *
     * @param procId
     *            the process id that describes this process
     * @throws IOException
     *             if something goes wrong with creating the process reference
     */
    public Win32Process(final int procId) throws IOException {
        handle = Kernel32.INSTANCE.OpenProcess(
                PROCESS_QUERY_INFORMATION | PROCESS_SUSPEND_RESUME
                    | PROCESS_TERMINATE | SYNCHRONIZE,
                false, procId);
        if (handle == null) {
            throw new IOException("OpenProcess failed: "
                    + Kernel32Util.formatMessageFromLastErrorCode(
                            Kernel32.INSTANCE.GetLastError()
                            )
                    );
        }
        this.setPid(procId);
    }

    @Override
    protected void finalize() throws Throwable {
        Kernel32.INSTANCE.CloseHandle(handle);
    }

    /**
     * Terminates the process that is represented by the given handle.
     */
    public void terminate() {
        Kernel32.INSTANCE.TerminateProcess(handle, 0);
    }

    /**
     * Gets the pid.
     *
     * @return the pid
     */
    public int getPid() {
        return pid;
    }

    /**
     * Sets the pid.
     *
     * @param procId
     *            the new pid
     */
    public void setPid(final int procId) {
        this.pid = procId;
    }
}
