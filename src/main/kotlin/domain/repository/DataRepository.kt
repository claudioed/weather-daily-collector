package domain.repository

import domain.service.Data

/**
 * Data repository
 * @author Claudio E. de Oliveira<claudioed.oliveira@gmail.com>
 *
 */
interface DataRepository {

    fun insert(data:Data)

}