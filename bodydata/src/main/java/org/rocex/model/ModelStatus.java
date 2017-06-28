package org.rocex.model;

/***************************************************************************
 * <br>
 * @author Rocex Wang
 * @version 2017-6-28 11:23:16
 ***************************************************************************/
public enum ModelStatus
{
    NOCHANGE(0), NEW(1), MODIFIED(2), DELETED(3);
    
    private int iStatus;
    
    ModelStatus(int iStatus)
    {
        this.iStatus = iStatus;
    }
    
    public int getStatus()
    {
        return iStatus;
    }
}
