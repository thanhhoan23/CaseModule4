class AppBase {

    static DOMAIN = location.origin;
    static API_SAVE = this.DOMAIN + '/api/products';
    static API_SIZE =this.DOMAIN + '/api/products/size';
    static API_COLOR =this.DOMAIN + '/api/products/color';
    static API_CATEGORY = this.DOMAIN + '/api/products/category'
    static API_DELETE = this.DOMAIN + '/api/products';
    static API_UPDATE = this.DOMAIN + '/api/products';
    static API_DO_UPDATE = this.DOMAIN + '/api/products';
    static API_PRODUCT_SEARCH = this.DOMAIN + '/api/products';


    static API_PRODUCT = this.DOMAIN + '/api/products';

    static SERVER_CLOUDINARY = "https://res.cloudinary.com";
    static CLOUDINARY_NAME = "/dly7s5c1q";
    static CLOUDINARY_SCALE = "c_limit,w_120,h_100,q_100";
    static CLOUDINARY_SCALE_280_200 = "c_limit,w_280,h_200,q_100";

    static CLOUDINARY_URL = this.SERVER_CLOUDINARY + this.CLOUDINARY_NAME + '/image/upload' + '/' + this.CLOUDINARY_SCALE;

    static AlertMessageEn = class {
        static SUCCESS_CREATED = "Successful data generation !";
        static SUCCESS_UPDATED = "Data update successful !";
        static SUCCESS_DEPOSIT = "Deposit transaction successful !";
        static SUCCESS_WITHDRAW = "Withdrawal transaction successful !";
        static SUCCESS_TRANSFER = "Transfer transaction successful !";
        static SUCCESS_DEACTIVATE = "Deactivate the customer successfully !";

        static ERROR_400 = "The operation failed, please check the data again.";
        static ERROR_401 = "Unauthorized - Your access token has expired or is not valid.";
        static ERROR_403 = "Forbidden - You are not authorized to access this resource.";
        static ERROR_404 = "Not Found - The resource has been removed or does not exist";
        static ERROR_500 = "Internal Server Error - The server system is having problems or cannot be accessed.";

        static ERROR_CREATED = 'Adding new customer failed';

        static ERROR_LOADING_PROVINCE = "Loading list of provinces - cities failed !";
        static ERROR_LOADING_DISTRICT = "Loading list of district - ward failed !"
        static ERROR_LOADING_WARD = "Loading list of wards - communes - towns failed !";

        static ERROR_LOADING_PRODUCT = "Loading list of products failed !"
    }

    static AlertMessageVi = class {
        static SUCCESS_CREATED = "Tạo dữ liệu thành công !";
        static SUCCESS_UPDATED = "Cập nhật dữ liệu thành công !";
        static SUCCESS_DEPOSIT = "Giao dịch gửi tiền thành công !";
        static SUCCESS_WITHDRAW = "Giao dịch rút tiền thành công !";
        static SUCCESS_TRANSFER = "Giao dịch chuyển khoản thành công !";
        static SUCCESS_DEACTIVATE = "Hủy kích hoạt khách hàng thành công !";

        static ERROR_400 = "Thao tác không thành công, vui lòng kiểm tra lại dữ liệu.";
        static ERROR_401 = "Unauthorized - Access Token của bạn hết hạn hoặc không hợp lệ.";
        static ERROR_403 = "Forbidden - Bạn không được quyền truy cập tài nguyên này.";
        static ERROR_404 = "Not Found - Tài nguyên này đã bị xóa hoặc không tồn tại";
        static ERROR_500 = "Internal Server Error - Hệ thống Server đang có vấn đề hoặc không truy cập được.";

        static ERROR_CREATED = 'Thêm khách hàng mới không thành công';

        static ERROR_LOADING_PROVINCE = "Tải danh sách tỉnh - thành phố không thành công !";
        static ERROR_LOADING_DISTRICT = "Tải danh sách quận - phường - huyện không thành công !";
        static ERROR_LOADING_WARD = "Tải danh sách phường - xã - thị trấn không thành công !";
    }

    static renderErrorCre(errors) {
        let str = "<div class='bg-danger' style='margin-top: 20px; padding: 10px 0'><ul>";
        for (let i = 0; i < errors.length; i++) {
            str += `<li><h4>${errors[i]}</h4></li>`;
        }
        str += "</ul></div>";
        return str;
    }

    static showSuspendedConfirmDialog() {
        return Swal.fire({
            icon: 'warning',
            text: 'Are you sure to suspend the selected customer ?',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, please suspend this client !',
            cancelButtonText: 'Cancel',
        })
    }

    static IziToast = class {
        static showSuccessAlert(m) {
            iziToast.success({
                title: 'OK',
                position: 'topRight',
                timeout: 2500,
                message: m
            });
        }

        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                timeout: 2500,
                message: m
            });
        }
    }

    static showSuccessAlert(t) {
        Swal.fire({
            icon: 'success',
            title: t,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500
        })
    }

    static showErrorAlert(t) {
        Swal.fire({
            icon: 'error',
            title: 'Warning',
            text: t,
        })
    }

    static formatNumber() {
        $(".num-space").number(true, 0, ',', ' ');
        $(".num-point").number(true, 0, ',', '.');
        $(".num-comma").number(true, 0, ',', ',');
    }

    static formatNumberSpace(x) {
        return x.toString().replace(/ /g, "").replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }

    static removeFormatNumberSpace(x) {
        return x.toString().replace(/ /g, "")
    }

    static formatTooltip() {
        $('[data-toggle="tooltip"]').tooltip();
    }




    // static renderProduct(item) {
    //     return `
    //         <tr id="tr_${item.id}">
    //             <td>${item.id}</td>
    //             <td>
    //                 <img src="${AppBase.CLOUDINARY_URL + '/' + item.fileFolder + '/' + item.fileName}" alt="" />
    //             </td>
    //             <td>${item.title}</td>
    //             <td>${item.price}</td>
    //             <td>${item.quantity}</td>
    //             <td>${item.description}</td>
    //             <td>${item.color}</td>
    //             <td>${item.size}</td>
    //             <td>${item.category}</td>
    //             <td class="text-center">
    //                 <a class="btn btn-outline-secondary edit" title="Edit" data-toggle="tooltip" data-id="${item.id}">
    //                     <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
    //                 </a>
    //             </td>
    //             <td class="text-center">
    //                 <a class="btn btn-outline-danger delete" title="Delete" data-toggle="tooltip" data-id="${item.id}" >
    //                     <i class="fa fa-ban" aria-hidden="true"></i>
    //                 </a>
    //             </td>
    //         </tr>
    //     `;
    // }
}


class Color {
    constructor(id, nameColor) {
        this.id = id;
        this.nameColor = nameColor;
    }
}

class Size {
    constructor(id, numberSize) {
        this.id = id;
        this.numberSize = numberSize;
    }
}
class Category {
    constructor(id, typeProduct) {
        this.id = id;
        this.typeProduct = typeProduct;
    }
}

class ProductMedia{
    constructor( id,  fileName,  fileFolder,  fileUrl,  fileType,  cloudId,  product) {
        this.id = id;
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
        this.fileType =fileType;
        this.cloudId = cloudId;
        this.product = product
    }
}
class Product{
    constructor(id, title, price, quantity, description, color, size, category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.color = color;
        this.size = size;
        this.category = category
    }
}