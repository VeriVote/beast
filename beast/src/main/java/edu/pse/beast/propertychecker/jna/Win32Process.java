package edu.pse.beast.propertychecker.jna;

import java.io.IOException;

//thanks for the code to: http://stackoverflow.com/a/10124625

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.WinNT;

public class Win32Process {
    /**
     * the windows new technology handle for this process
     */
    WinNT.HANDLE handle;

    /**
     * the process id for this process
     */
    int pid;

    /**
     * creates a new Win32 Process given a process id
     *
     * @param pid the process id that describes this process
     * @throws IOException if something goes wrong with creating the process
     *                     reference
     */
    public Win32Process(int pid) throws IOException {
        handle = Kernel32.INSTANCE.OpenProcess(0x0400 | /* PROCESS_QUERY_INFORMATION */
                0x0800 | /* PROCESS_SUSPEND_RESUME */
                0x0001 | /* PROCESS_TERMINATE */
                0x00100000 /* SYNCHRONIZE */, false, pid);
        if (handle == null) {
            throw new IOException("OpenProcess failed: "
                    + Kernel32Util.formatMessageFromLastErrorCode(Kernel32.INSTANCE.GetLastError()));
        }
        this.pid = pid;
    }

    @Override
    protected void finalize() throws Throwable {
        Kernel32.INSTANCE.CloseHandle(handle);
    }

    /**
     * terminates the process that is represented by the given handle
     */
    public void terminate() {
        Kernel32.INSTANCE.TerminateProcess(handle, 0);
    }
}