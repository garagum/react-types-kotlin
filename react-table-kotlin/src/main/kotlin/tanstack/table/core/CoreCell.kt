// Automatically generated - do not modify!

package tanstack.table.core

type CoreCell<TData extends RowData> = {
    id: string
    getValue: () => any
    renderValue: () => unknown
    row: Row<TData>
    column: Column<TData>
    getContext: () => {
        table: Table<TData>
        column: Column<TData>
        row: Row<TData>
        cell: Cell<TData>
        getValue: () => any
        renderValue: () => any
    }
}
