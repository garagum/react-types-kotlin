// Automatically generated - do not modify!

package typescript

sealed external interface PropertySignature : TypeElement, JSDocContainer, Union.PropertySignature_ {
    override val kind: SyntaxKind.PropertySignature
    override val modifiers: NodeArray<Modifier>?
    override val name: PropertyName
    override val questionToken: QuestionToken?
    val type: TypeNode?

    /** @deprecated A property signature cannot have an initializer */
    val initializer: Expression?
}
