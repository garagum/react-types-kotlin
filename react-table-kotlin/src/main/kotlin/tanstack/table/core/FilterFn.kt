// Automatically generated - do not modify!

package tanstack.table.core

external interface FilterFn<TData : RowData> {
    var (row: Row<TData>, columnId: string, filterValue: any, addMeta: (meta: any) -> Unit): boolean
    var resolveFilterValue: TransformFilterValueFn<TData>?
    var autoRemove: ColumnFilterAutoRemoveTestFn<TData>?
}
