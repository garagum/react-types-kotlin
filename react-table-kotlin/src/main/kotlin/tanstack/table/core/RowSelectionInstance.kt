// Automatically generated - do not modify!

package tanstack.table.core

external interface RowSelectionInstance<TData : RowData> {
    var getToggleAllRowsSelectedHandler: () -> (event: unknown) -> Unit
    var getToggleAllPageRowsSelectedHandler: () -> (event: unknown) -> Unit
    var setRowSelection: (updater: Updater<RowSelectionState>) -> Unit
    var resetRowSelection: (defaultState?: boolean) -> Unit
    var getIsAllRowsSelected: () -> Boolean
    var getIsAllPageRowsSelected: () -> Boolean
    var getIsSomeRowsSelected: () -> Boolean
    var getIsSomePageRowsSelected: () -> Boolean
    var toggleAllRowsSelected: (value?: boolean) -> Unit
    var toggleAllPageRowsSelected: (value?: boolean) -> Unit
    var getPreSelectedRowModel: () -> RowModel<TData>
    var getSelectedRowModel: () -> RowModel<TData>
    var getFilteredSelectedRowModel: () -> RowModel<TData>
    var getGroupedSelectedRowModel: () -> RowModel<TData>
}
