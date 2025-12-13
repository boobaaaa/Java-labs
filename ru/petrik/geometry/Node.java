package ru.petrik.geometry;

public class Node {
    private Integer value;
    private Node parent;
    private Node left;
    private Node right;

    public Node() {
        this.value = null;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    public void addValue(int newValue) {
        if (value == null) {
            value = newValue;
            return;
        }
        
        if (newValue > value) {
            if (right == null) {
                right = new Node();
                right.parent = this;
            }
            right.addValue(newValue);
        } else {
            if (left == null) {
                left = new Node();
                left.parent = this;
            }
            left.addValue(newValue);
        }
    }

    public boolean contains(int searchValue) {
        if (value == null) return false;
        
        if (value == searchValue) return true;
        
        if (searchValue > value) {
            return right != null && right.contains(searchValue);
        } else {
            return left != null && left.contains(searchValue);
        }
    }

    public void removeValue(int removeValue) {
        Node nodeToRemove = findNode(removeValue);
        if (nodeToRemove == null) return;
        
        if (nodeToRemove.parent == null) {
            if (nodeToRemove.left != null) {
                Node maxLeft = nodeToRemove.left;
                while (maxLeft.right != null) {
                    maxLeft = maxLeft.right;
                }
                nodeToRemove.value = maxLeft.value;
                if (maxLeft.parent.left == maxLeft) {
                    maxLeft.parent.left = maxLeft.left;
                    if (maxLeft.left != null) {
                        maxLeft.left.parent = maxLeft.parent;
                    }
                } else {
                    maxLeft.parent.right = maxLeft.left;
                    if (maxLeft.left != null) {
                        maxLeft.left.parent = maxLeft.parent;
                    }
                }
            } else if (nodeToRemove.right != null) {
                Node minRight = nodeToRemove.right;
                while (minRight.left != null) {
                    minRight = minRight.left;
                }
                nodeToRemove.value = minRight.value;
                if (minRight.parent.left == minRight) {
                    minRight.parent.left = minRight.right;
                    if (minRight.right != null) {
                        minRight.right.parent = minRight.parent;
                    }
                } else {
                    minRight.parent.right = minRight.right;
                    if (minRight.right != null) {
                        minRight.right.parent = minRight.parent;
                    }
                }
            } else {
                nodeToRemove.value = null;
            }
            return;
        }
        
        removeNode(nodeToRemove);
    }
    
    private Node findNode(int searchValue) {
        if (value == null) return null;
        
        if (value == searchValue) return this;
        
        if (searchValue > value) {
            return right != null ? right.findNode(searchValue) : null;
        } else {
            return left != null ? left.findNode(searchValue) : null;
        }
    }
    
    private void removeNode(Node node) {
        if (node == null) return;
        
        if (node.left == null && node.right == null) {
            if (node.parent.left == node) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            return;
        }
        
        if (node.left == null || node.right == null) {
            Node child = (node.left != null) ? node.left : node.right;
            
            if (node.parent.left == node) {
                node.parent.left = child;
            } else {
                node.parent.right = child;
            }
            
            if (child != null) {
                child.parent = node.parent;
            }
            return;
        }
        
        Node minRight = node.right;
        while (minRight.left != null) {
            minRight = minRight.left;
        }
        
        node.value = minRight.value;
        
        removeNode(minRight);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        inorderTraversal(result);
        return result.toString().trim();
    }
    
    private void inorderTraversal(StringBuilder sb) {
        if (left != null) {
            left.inorderTraversal(sb);
        }
        
        if (value != null) {
            sb.append(value).append(" ");
        }
        
        if (right != null) {
            right.inorderTraversal(sb);
        }
    }
}