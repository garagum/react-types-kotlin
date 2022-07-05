// Automatically generated - do not modify!

package tanstack.table.core

external interface RowSelectionInstance<TData : RowData> {
    var getToggleAllRowsSelectedHandler: () => (event: unknown) => void
    var getToggleAllPageRowsSelectedHandler: () => (event: unknown) => void
    var setRowSelection: (updater: Updater<RowSelectionState>) => void
    var resetRowSelection: (defaultState?: boolean) => void
    var getIsAllRowsSelected: () => boolean
    var getIsAllPageRowsSelected: () => boolean
    var getIsSomeRowsSelected: () => boolean
    var getIsSomePageRowsSelected: () => boolean
    var toggleAllRowsSelected: (value?: boolean) => void
    var toggleAllPageRowsSelected: (value?: boolean) => void
    var getPreSelectedRowModel: () => RowModel<TData>
    var getSelectedRowModel: () => RowModel<TData>
    var getFilteredSelectedRowModel: () => RowModel<TData>
    var getGroupedSelectedRowModel: () => RowModel<TData>
}
