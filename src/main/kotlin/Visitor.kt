
/**
 * Interface that utilizes the `Visitor Pattern` allowing to Iterate over the [Tag]'s data structure.
 */
interface Visitor {
    fun visit(d: VaultTecDweller): Boolean = true
    fun endVisit(d: VaultTecDweller) //TODO remove if unnecessary
}