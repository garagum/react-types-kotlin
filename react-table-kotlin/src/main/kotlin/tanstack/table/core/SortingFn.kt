// Automatically generated - do not modify!

package tanstack.table.core

external interface SortingFn<TData : RowData> {
    (rowA: Row<TData>, rowB: Row<TData>, columnId: string): number
}
