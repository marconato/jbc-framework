package br.com.jbc.util;

import java.io.Serializable;
import java.util.List;

public class SearchPaginate implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Contém a lista páginada
     */
    private List listResult;
    
    /**
     * Contém a quantidade total de registros da entidade
     */
    private int rowCount;

    /**
     * @return the listResult
     */
    public List getListResult() {
        return listResult;
    }

    /**
     * @param listResult the listResult to set
     */
    public void setListResult(List listResult) {
        this.listResult = listResult;
    }

    /**
     * @return the rowCount
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * @param rowCount the rowCount to set
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
