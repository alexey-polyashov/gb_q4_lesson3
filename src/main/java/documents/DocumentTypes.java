package documents;

public enum DocumentTypes {
    LETTER(Letter.class),
    ARTICLE(Article.class),
    ORDER(Order.class),
    APPENDIX(Appendix.class),
    APPROVAL_SHEET(ApprovalSheet.class),
    COMPOSITE_ORDER(CompositeOrder.class),
    EMAIL(Email.class);

    Class documentClass;

    public Class getDocumentClass() {
        return documentClass;
    }

    DocumentTypes(Class documentClass) {
        this.documentClass = documentClass;
    }
}
